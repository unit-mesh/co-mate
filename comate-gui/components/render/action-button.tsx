import * as React from "react";

import { Button } from "@/components/ui/button";
import { ToolingThought } from "@/components/render/message-render";

export function ActionButton({ isPending, tooling, onResult }: {
  isPending: boolean,
  tooling: ToolingThought,
  onResult: (result: string) => void
}) {
  return <Button
    variant="outline"
    disabled={isPending}
    onClick={async () => {
      isPending = true

      await fetch("/api/action/tooling", {
        method: "POST",
        body: JSON.stringify(tooling),
      }).then(async response => {
        onResult(await response.json())
        isPending = false
      });
    }}>{isPending ? "Pending..." : "Run"}</Button>;
}
