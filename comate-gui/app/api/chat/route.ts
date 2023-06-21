import { Message, OpenAIStream, StreamingTextResponse } from 'ai'
import { Configuration, OpenAIApi } from 'openai-edge'
import { searchTooling } from "@/app/api/common/search-tooling";

// import { auth } from '@/auth'
// import { nanoid } from '@/lib/utils'

// export const runtime = 'edge'

export async function requestToOpenAi(previewToken: string, messages: Message[], isStream: boolean = true) {
  let basePath = process.env.PROXY_URL
  if (basePath == null) {
    basePath = 'https://api.openai.com'
  }

  const configuration = new Configuration({
    apiKey: previewToken || process.env.OPENAI_API_KEY,
    basePath
  })

  const openai = new OpenAIApi(configuration)

  const res = await openai.createChatCompletion({
    model: 'gpt-3.5-turbo',
    messages,
    temperature: 0.7,
    stream: isStream
  })

  console.log("isStream", isStream)
  if (!isStream) {
    return res
  }

  const stream = OpenAIStream(res, {

  })

  return new StreamingTextResponse(stream)
}

export async function POST(req: Request) {
  const json = await req.json()
  const { messages, previewToken } = json

  if (messages.length == 1) {
    // post to /api/prompt
    let output = await searchTooling(messages[0].content);
    messages[0].content = output.prompt;
  }

  // const session = await auth()

  // if (process.env.VERCEL_ENV !== 'preview') {
  //   if (session == null) {
  //     return new Response('Unauthorized', { status: 401 })
  //   }
  // }

  // wrapper text to our server
  return await requestToOpenAi(previewToken, messages);
}
