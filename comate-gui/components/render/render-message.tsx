import { Message } from "ai";
import { renderMarkdown } from "@/components/render/render-markdown";

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

  console.log(thoughtRegex.test(firstLine));
  if (splitContent.length >= 1 && thoughtRegex.test(firstLine)) {
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

    content = `${thought}\n | Action | Action Input |\n | ------ | ------------ |\n | ${action} | ${actionInput} | \n\n ${others.join('\n')}`;
  }

  console.log(content)
  return renderMarkdown(content);
}
