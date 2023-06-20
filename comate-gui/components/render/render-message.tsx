import { Message } from "ai";
import { renderMarkdown } from "@/components/render/render-markdown";
import { Button } from "@/components/ui/button";

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

export function renderMessage(message: Message) {
  let content = message.content;

  let splitContent = content.split('\n').filter((line) => line.trim() !== "");
  let firstLine = splitContent[0];

  let isOurThoughtTree = splitContent.length < 1 || !thoughtRegex.test(firstLine);
  if (isOurThoughtTree) {
    return renderMarkdown(content);
  }

  let thought = thoughtRegex.exec(firstLine)?.[1] ?? "";
  let action = ""
  if (splitContent.length >= 2) {
    action = actionRegex.exec(splitContent[1])?.[1] ?? "";
  }

  let actionInput = ""
  if (splitContent.length >= 3) {
    actionInput = actionInputRegex.exec(splitContent[2])?.[1] ?? "";
  }

  let toolingThought: ToolingThought = {
    thought: thought,
    action: action,
    actionInput: actionInput
  }

  let others = splitContent.slice(3);
  let isPending = false;

  return <>
    {renderMarkdown(thought)}

    <table>
      <thead>
      <tr>
        <th>Action</th>
        <th>Action Input</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>{action}</td>
        <td>{actionInput}</td>
        <td><Button variant="outline" disabled={isPending}>{isPending ? "Pending..." : "Run"}</Button></td>
      </tr>
      </tbody>
    </table>

    {renderMarkdown(others.join('\n'))}
  </>;
}
