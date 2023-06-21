import { Message, OpenAIStream, streamToResponse } from "ai";
import { MarkdownRender } from "@/components/render/markdown-render";
import { Button } from "@/components/ui/button";
import * as React from "react";
import { decodeAIStreamChunk, fetcher } from "@/lib/utils";

export interface ToolingThought {
  thought: string
  action: string
  actionInput: string
}

// Thought: I need to introduce the system to the team and ensure that it aligns with our overall architecture and governance policies.
// Action: introduce_system
// Action Input: https://github.com/archguard/ddd-monolithic-code-sample
const thoughtRegex = /\s*Thought:\s*(.*(?:\n(?!\s*\/\/).*)*)/i;
const actionRegex = /\s*Action:\s*(.*(?:\n(?!\s*\/\/).*)*)/i;
const actionInputRegex = /\s*Action\s*Input:\s*(.*(?:\n(?!\s*\/\/).*)*)/i;

function messageToThought(firstLine: string, splitContent: string[]) {
  let thought = thoughtRegex.exec(firstLine)?.[1] ?? "";
  let action = ""
  if (splitContent.length >= 2) {
    action = actionRegex.exec(splitContent[1])?.[1] ?? "";
  }

  let actionInput = ""
  if (splitContent.length >= 3) {
    actionInput = actionInputRegex.exec(splitContent[2])?.[1] ?? "";
  }

  let tooling: ToolingThought = {
    thought: thought,
    action: action,
    actionInput: actionInput
  }
  return tooling;
}

function ActionButton({ isPending, tooling, onResult }: {
  isPending: boolean,
  tooling: ToolingThought,
  onResult: (result: string) => void
}) {
  return <Button
    variant="outline"
    disabled={isPending}
    onClick={async () => {
      isPending = true

      await fetch("/api/action/tooling", {
        method: "POST",
        body: JSON.stringify(tooling),
      }).then(async response => {
        onResult(await response.json())
        // let result = ""
        // const reader = response.body.getReader()
        // while (true) {
        //   const { done, value } = await reader.read()
        //   if (done) {
        //     break
        //   }
        //
        //   result += decodeAIStreamChunk(value)
        //   onResult(result)
        // }

        isPending = false
      });
    }}>{isPending ? "Pending..." : "Run"}</Button>;
}

type MessageRenderProps = { message: Message, chatId?: string, appendToChat?: (message: Message) => void };

export function MessageRender({ message, appendToChat, chatId }: MessageRenderProps) {
  const [isPending, setIsPending] = React.useState(false)
  // const [actionResult, setActionResult] = React.useState("")

  let content = message.content;

  let splitContent = content.split('\n').filter((line) => line.trim() !== "");
  let firstLine = splitContent[0];

  let isOurThoughtTree = splitContent.length < 1 || !thoughtRegex.test(firstLine);
  if (isOurThoughtTree) {
    return <MarkdownRender content={content}/>;
  }

  let tooling = messageToThought(firstLine, splitContent);
  let others = splitContent.slice(3);

  return <>
    <MarkdownRender content={tooling.thought}/>

    <table className="bg-white">
      <thead>
      <tr>
        <th>Action</th>
        <th>Action Input</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>{tooling.action}</td>
        <td>{tooling.actionInput}</td>
        <td>
          <ActionButton isPending={isPending} tooling={tooling} onResult={
            (output: any) => {
              console.log(output);
              // setActionResult(output)
              appendToChat?.({
                id: chatId!!,
                content: output.action,
                role: 'user'
              });
            }
          }/></td>
      </tr>
      </tbody>
    </table>

    {/*{actionResult && <MarkdownRender content={actionResult}/>}*/}

    <MarkdownRender content={others.join('\n')}/>
  </>;
}
