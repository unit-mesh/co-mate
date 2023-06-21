import * as React from "react";

import { Button } from "@/components/ui/button";
import { ToolingThought } from "@/components/render/message-render";

export function ActionButton({ tooling, onResult }: {
  tooling: ToolingThought,
  onResult: (result: string) => void
}) {
  const [isAnalysing, setIsAnalysing] = React.useState(false)
  console.log("actionButton: " + tooling)

  return <Button
    variant="outline"
    disabled={isAnalysing}
    onClick={async () => {
      setIsAnalysing(true)

      await fetch("/api/action/tooling", {
        method: "POST",
        body: JSON.stringify(tooling),
      }).then(async response => {
        onResult(await response.json())
        setIsAnalysing(false)
      });
    }}>{isAnalysing ? "Analysis..." : "Run"}</Button>;
}
