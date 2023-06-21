import { NextResponse } from "next/server";

export const runtime = 'edge'

export async function POST(req: Request) {
  const json = await req.json()

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

    let responseJson = await response.json();
    return NextResponse.json(responseJson, { status: 200 })
  } catch (e) {
    return NextResponse.json({ error: e }, { status: 500 })
  }
}
