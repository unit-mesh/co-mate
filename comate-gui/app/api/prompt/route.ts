const PREFIX = `Answer the following questions as best you can. You have access to the following tools:`
const formatInstructions = (toolNames: string) => `
Use the following format:

Question: the input question you must answer
Thought: you should always think about what to do
Action: the action to take, should be one of [${toolNames}]
Action Input: the input to the action
`
const suffix = (input: string) => `
Begin!

Question: ${input}
`

const functionSearch = (text: string, tools: string[]) => {
  const toolStrings = tools.join('\n')
  const toolNames = tools.join(', ')
  const instructions = formatInstructions(toolNames)

  return `${PREFIX}
  
${toolStrings}

${instructions}

${suffix(text)}`
}

export async function POST(req: Request) {

}
