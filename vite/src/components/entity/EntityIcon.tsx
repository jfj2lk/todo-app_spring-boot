import { Button } from "@/components/ui/button";
import { ReactNode } from "react";

const EntityIcon = (props: { children: ReactNode; className?: string }) => {
  return (
    <Button
      className={`entity-manager_icon ${props.className}`}
      variant={"ghost"}
      size={"icon"}
    >
      {props.children}
    </Button>
  );
};

export { EntityIcon };
