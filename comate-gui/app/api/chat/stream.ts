import {Message, OpenAIStream, StreamingTextResponse} from "ai";
import {Configuration, OpenAIApi} from "openai-edge";

export async function stream(apiKey: string, messages: Message[], isStream: boolean = true) {
    let basePath = process.env.OPENAI_PROXY_URL
    if (basePath == null) {
        basePath = 'https://api.openai.com'
    }

    const configuration = new Configuration({
        apiKey: apiKey || process.env.OPENAI_API_KEY,
        basePath
    })

    const openai = new OpenAIApi(configuration)

    const res = await openai.createChatCompletion({
        model: 'gpt-3.5-turbo',
        messages,
        temperature: 0.7,
        stream: isStream
    })

    if (!isStream) {
        return res
    }

    const stream = OpenAIStream(res, {})

    return new StreamingTextResponse(stream)
}