import { Button } from "@/components/ui/button";
import { ComponentPropsWithoutRef, forwardRef, ReactNode } from "react";

type EntityIconProps = {
  children: ReactNode;
} & ComponentPropsWithoutRef<"button">;

const BaseButton = forwardRef<HTMLButtonElement, EntityIconProps>(
  (props, ref) => {
    return (
      <Button ref={ref} variant="ghost" className="text-gray-500" {...props}>
        {props.children}
      </Button>
    );
  },
);

export { BaseButton };
