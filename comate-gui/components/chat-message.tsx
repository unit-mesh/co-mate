import { Message } from 'ai'

import { cn } from '@/lib/utils'
import { IconOpenAI, IconUser } from '@/components/ui/icons'
import { ChatMessageActions } from '@/components/chat-message-actions'
import { MessageRender } from "@/components/render/message-render";

export interface ChatMessageProps {
  message: Message
  chatId?: string
}

export function ChatMessage({ message, chatId, ...props }: ChatMessageProps) {
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
        <MessageRender message={message} chatId={chatId}/>
        <ChatMessageActions message={message}/>
      </div>
    </div>
  )
}
