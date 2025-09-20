import { BaseSidebarGroup } from "@/components/common/BaseSidebarGroup";
import { useAppSelector } from "@/store";
import {
  createLabel,
  deleteLabel,
  getAllLabels,
  labelSelectors,
  updateLabel,
} from "@/store/label-store";
import { defaultLabelFormValues, labelFormSchema } from "@/types/label";
import { Tag } from "lucide-react";

const LabelGroup = () => {
  const labels = useAppSelector(labelSelectors.selectAll);

  return (
    <BaseSidebarGroup
      entity={labels}
      getEntity={getAllLabels}
      createEntity={createLabel}
      updateEntity={updateLabel}
      deleteEntity={deleteLabel}
      formSchema={labelFormSchema}
      defaultFormValues={defaultLabelFormValues}
      labelName="ラベル"
      resourceName="labels"
      entityIcon={<Tag size={16} />}
    />
  );
};

export { LabelGroup };
