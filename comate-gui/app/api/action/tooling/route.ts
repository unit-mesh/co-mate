export const runtime = 'edge'


export async function POST(req: Request) {
  const json = await req.json()
  let baseUrl = process.env.COMATE_BACKEND || 'http://localhost:8844';
  let url = `${baseUrl}/api/action/tooling`

  const response = await fetch(url, {
    method: 'POST',
    body: JSON.stringify(json),
  });
  //

  console.log(response)
  console.log(response.text())

  // await streamPipeline(response.body, createWriteStream('./octocat.png'));
}
