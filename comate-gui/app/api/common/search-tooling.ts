import fetch from 'node-fetch';

export async function searchTooling(text: string | undefined) {
  let baseUrl = process.env.COMATE_BACKEND || 'http://localhost:8844';
  let url = `${baseUrl}/api/prompt/tooling`

  const res = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ text: text })
  });

  console.log(res);

  // const json = await res.json()
  // console.log(json)
  return {};
}
