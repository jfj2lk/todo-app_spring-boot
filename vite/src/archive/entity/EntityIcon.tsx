import { Button } from "@/components/ui/button";
import { ReactNode } from "react";

const EntityIcon = (props: { children: ReactNode; className?: string }) => {
  const { children, className, ...rest } = props;

  return (
    <Button
      className={`entity-manager_icon ${props.className}`}
      variant={"ghost"}
      size={"icon"}
      {...rest}
    >
      {props.children}
    </Button>
  );
};

export { EntityIcon };
