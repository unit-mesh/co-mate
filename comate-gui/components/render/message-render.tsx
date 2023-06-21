import { Message } from "ai";
import { MarkdownRender } from "@/components/render/markdown-render";
import * as React from "react";
import { ActionButton } from "@/components/render/action-button";

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

type MessageRenderProps = { message: Message, chatId?: string, appendToChat?: (message: Message) => void };

export function MessageRender({ message, appendToChat, chatId }: MessageRenderProps) {
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
          <ActionButton tooling={tooling} onResult={
            (output: any) => {
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

    <MarkdownRender content={others.join('\n')}/>
  </>;
}
