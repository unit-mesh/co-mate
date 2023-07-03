import {searchTooling} from "@/app/api/common/search-tooling";
import {stream} from "@/app/api/chat/stream";

// import { auth } from '@/auth'
// import { nanoid } from '@/lib/utils'

export const runtime = 'edge'

export async function POST(req: Request) {
  const json = await req.json()
  const { messages, previewToken } = json

  // if (messages.length == 1) {
  // get last messages

  let lastMessage = messages[messages.length - 1]
  // console.log(lastMessage)

  // todo: define prompt for our ai
  let isComatePrompt = lastMessage.content && lastMessage.content.startsWith("You're an Architecture");
  if (!isComatePrompt) {
    // if output.prompt includes "HasMatchFunction: true" then we need to add a new message to the messages array
    let output = await searchTooling(lastMessage.content);
    lastMessage.content = output.prompt;
  }

  // const session = await auth()

  // if (process.env.VERCEL_ENV !== 'preview') {
  //   if (session == null) {
  //     return new Response('Unauthorized', { status: 401 })
  //   }
  // }

  // wrapper text to our server
  return await stream(previewToken, messages);
}
