"use client";

import { EntityManager } from "@/components/entity/EntityManager";
import { useAppSelector } from "@/store";
import {
  createLabel,
  deleteLabel,
  getAllLabels,
  labelSelectors,
  updateLabel,
} from "@/store/labelStore";
import { defaultLabelFormValues, labelFormSchema } from "@/types/label";
import { Tag } from "lucide-react";

const LabelPage = () => {
  const labels = useAppSelector(labelSelectors.selectAll);

  return (
    <div className="container mx-auto max-w-4xl space-y-6 p-6">
      <EntityManager
        entities={labels}
        formSchema={labelFormSchema}
        defaultFormValues={defaultLabelFormValues}
        getAllEntities={getAllLabels}
        createEntity={createLabel}
        updateEntity={updateLabel}
        deleteEntity={deleteLabel}
        entityName="ラベル"
        entityIcon={<Tag />}
      />
    </div>
  );
};

export default LabelPage;
