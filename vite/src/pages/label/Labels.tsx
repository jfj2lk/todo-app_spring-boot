"use client";

import { EntityManager } from "@/components/entity/EntityManager";
import {
  createEntity,
  deleteEntity,
  getAllEntities,
  updateEntity,
} from "@/components/entity/logic/entity-api";
import { entitySelectors } from "@/components/entity/logic/entity-state";
import { Button } from "@/components/ui/button";
import { useAppSelector } from "@/store";
import { LabelType } from "@/types/label";
import { apiRequest } from "@/utils/api";
import { Circle, Plus } from "lucide-react";
import { useEffect, useState } from "react";
import { LabelList } from "./LabelList";
import { LabelModal } from "./LabelModal";

const Labels = () => {
  const [labels, setLabels] = useState<LabelType[]>([]);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingLabel, setEditingLabel] = useState<LabelType | null>(null);

  const onAddLabel = () => {
    setEditingLabel(null);
    setIsModalOpen(true);
  };

  const onEditLabel = (label: LabelType) => {
    setEditingLabel(label);
    setIsModalOpen(true);
  };

  const onCloseModal = () => {
    setIsModalOpen(false);
    setEditingLabel(null);
  };

  // Label追加
  const handleAddLabel = async (addLabel: { name: string }) => {
    const json = await apiRequest<LabelType>("/api/labels", "POST", addLabel);
    setLabels([...labels, json.data]);

    setIsModalOpen(false);
    setEditingLabel(null);
  };

  // Label更新
  const handleUpdateLabel = async (
    updateLabelId: number,
    updateLabel: { name: string },
  ) => {
    const json = await apiRequest<LabelType>(
      `/api/labels/${updateLabelId}`,
      "PATCH",
      updateLabel,
    );
    setLabels(
      labels.map((label) => (label.id === updateLabelId ? json.data : label)),
    );

    setIsModalOpen(false);
    setEditingLabel(null);
  };

  // Label削除
  const handleDeleteLabel = async (deleteLabelId: number) => {
    const json = await apiRequest<number>(
      `/api/labels/${deleteLabelId}`,
      "DELETE",
    );
    setLabels(labels.filter((label) => label.id !== json.data));
  };

  // 全てのLabelを取得し、stateを初期化する
  useEffect(() => {
    (async () => {
      const json = await apiRequest<LabelType[]>("/api/labels");
      setLabels(json.data);
    })();
  }, []);

  //
  const entities = useAppSelector(entitySelectors.selectAll);

  return (
    <div className="container mx-auto max-w-4xl p-6">
      <div className="space-y-6">
        {/*  */}

        <EntityManager
          entities={entities}
          getAllEntities={getAllEntities}
          createEntity={createEntity}
          updateEntity={updateEntity}
          deleteEntity={deleteEntity}
          entityName="ラベル"
          entityIcon={<Circle />}
          createEntityDefaults={{
            name: "",
            description: "",
          }}
        />

        {/*  */}
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-semibold">ラベル</h1>
          <Button
            variant="ghost"
            size="icon"
            onClick={onAddLabel}
            className="text-muted-foreground hover:text-foreground h-8 w-8"
          >
            <Plus className="h-4 w-4" />
          </Button>
        </div>

        <LabelList
          labels={labels}
          onEditLabel={onEditLabel}
          onDeleteLabel={handleDeleteLabel}
        />

        <LabelModal
          isOpen={isModalOpen}
          onClose={onCloseModal}
          handleAddLabel={handleAddLabel}
          handleUpdateLabel={handleUpdateLabel}
          editingLabel={editingLabel}
        />
      </div>
    </div>
  );
};

export default Labels;
