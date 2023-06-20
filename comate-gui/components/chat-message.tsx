import { Message } from 'ai'
import remarkGfm from 'remark-gfm'
import remarkMath from 'remark-math'

import { cn } from '@/lib/utils'
import { CodeBlock } from '@/components/ui/codeblock'
import { MemoizedReactMarkdown } from '@/components/markdown'
import { IconOpenAI, IconUser } from '@/components/ui/icons'
import { ChatMessageActions } from '@/components/chat-message-actions'

export interface ChatMessageProps {
  message: Message
}

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

function renderMessage(message: Message) {

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

  return <MemoizedReactMarkdown
    className="prose whitespace-pre-wrap break-words dark:prose-invert prose-p:leading-relaxed prose-pre:p-0"
    remarkPlugins={[remarkGfm, remarkMath]}
    components={{
      p({ children }) {
        return <p className="mb-2 last:mb-0">{children}</p>
      },
      code({ node, inline, className, children, ...props }) {
        if (children.length) {
          if (children[0] == '▍') {
            return (
              <span className="mt-1 animate-pulse cursor-default">▍</span>
            )
          }

          children[0] = (children[0] as string).replace('`▍`', '▍')
        }

        const match = /language-(\w+)/.exec(className || '')

        if (inline) {
          return (
            <code className={className} {...props}>
              {children}
            </code>
          )
        }

        return (
          <CodeBlock
            key={Math.random()}
            language={(match && match[1]) || ''}
            value={String(children).replace(/\n$/, '')}
            {...props}
          />
        )
      }
    }}
  >
    {content}
  </MemoizedReactMarkdown>;
}

export function ChatMessage({ message, ...props }: ChatMessageProps) {
  return (
    <div
      className={cn('group relative mb-4 flex items-start md:-ml-12')}
      {...props}
    >
      <div
        className={cn(
          'flex h-8 w-8 shrink-0 select-none items-center justify-center rounded-md border shadow',
          message.role === 'user'
            ? 'bg-background'
            : 'bg-primary text-primary-foreground'
        )}
      >
        {message.role === 'user' ? <IconUser/> : <IconOpenAI/>}
      </div>
      <div className="ml-4 flex-1 space-y-2 overflow-hidden px-1">
        {renderMessage(message)}
        <ChatMessageActions message={message}/>
      </div>
    </div>
  )
}
