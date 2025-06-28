import { BaseButton } from "@/components/common/BaseButton";
import { ChevronDown } from "lucide-react";

const CollapsibleTriggerButton = () => {
  return (
    <BaseButton>
      <ChevronDown className="transition-transform group-data-[state=open]/collapsible:rotate-180" />
    </BaseButton>
  );
};

export { CollapsibleTriggerButton };
