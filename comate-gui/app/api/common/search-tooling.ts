import fetch from 'node-fetch';

type BaseTool = {
  name: string,
  description: string,
}

type ToolingResponse = {
  prompt: string,
  tools: BaseTool[],
}

export async function searchTooling(text: string | undefined): Promise<ToolingResponse> {
  let baseUrl = process.env.COMATE_BACKEND || 'http://localhost:8844';
  let url = `${baseUrl}/api/prompt/tooling`

  const res = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ text: text })
  });

  const json = await res.json()
  return json as ToolingResponse;
}
