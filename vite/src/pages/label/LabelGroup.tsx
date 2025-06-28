import { BaseSidebarGroup } from "@/components/common/BaseSidebarGroup";
import { useAppSelector } from "@/store";
import {
  createLabel,
  deleteLabel,
  getAllLabels,
  labelSelectors,
  updateLabel,
} from "@/store/label-Store";
import { defaultLabelFormValues, labelFormSchema } from "@/types/label";

const LabelGroup = () => {
  const labels = useAppSelector(labelSelectors.selectAll);

  return (
    <BaseSidebarGroup
      entities={labels}
      getAllEntities={getAllLabels}
      createEntity={createLabel}
      updateEntity={updateLabel}
      deleteEntity={deleteLabel}
      formSchema={labelFormSchema}
      defaultFormValues={defaultLabelFormValues}
      labelName="ラベル"
    />
  );
};

export { LabelGroup };
