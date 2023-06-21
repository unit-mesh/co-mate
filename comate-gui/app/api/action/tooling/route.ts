import { NextResponse } from "next/server";
import { requestToOpenAi } from "@/app/api/chat/route";
import { Message } from "ai";

export const runtime = 'edge'

export async function POST(req: Request) {
  const json = await req.json()
  const { previewToken } = json

  console.log(previewToken);

  let baseUrl = process.env.COMATE_BACKEND || 'http://localhost:8844';
  let url = `${baseUrl}/api/action/tooling`

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(json),
    });

    let newAction = await response.json();
    const messages: any[] = [];
    messages.push({
      content: newAction.action,
      role: 'user'
    })

    console.log("tooling, newAction", newAction);
    return requestToOpenAi(previewToken, messages, false)
  } catch (e) {
    return NextResponse.json({ error: e }, { status: 500 })
  }
}