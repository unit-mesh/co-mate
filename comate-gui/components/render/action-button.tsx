import * as React from "react";

import { Button } from "@/components/ui/button";
import { ToolingThought } from "@/components/render/message-render";

export function ActionButton({ tooling, onResult }: {
  tooling: ToolingThought,
  onResult: (result: string) => void
}) {
  const [isAnalysising, setIsAnalysising] = React.useState(false)

  return <Button
    variant="outline"
    disabled={isAnalysising}
    onClick={async () => {
      setIsAnalysising(true)

      await fetch("/api/action/tooling", {
        method: "POST",
        body: JSON.stringify(tooling),
      }).then(async response => {
        onResult(await response.json())
        setIsAnalysising(false)
      });
    }}>{isAnalysising ? "Analysis..." : "Run"}</Button>;
}
