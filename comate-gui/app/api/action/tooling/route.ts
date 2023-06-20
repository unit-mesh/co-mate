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

    console.log(response)
    console.log(response.json())
  } catch (e) {
    console.log(e)
  }
}
