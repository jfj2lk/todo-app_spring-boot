"use client";

import { LabelType } from "@/types/label";
import { LabelItem } from "./LabelItem";

interface LabelListProps {
  labels: LabelType[];
  onEditLabel: (label: LabelType) => void;
  onDeleteLabel: (labelId: number) => void;
}

const LabelList = ({ labels, onEditLabel, onDeleteLabel }: LabelListProps) => {
  return (
    <div className="space-y-2">
      {labels.map((label) => (
        <LabelItem
          key={label.id}
          label={label}
          onEdit={() => onEditLabel(label)}
          onDelete={() => onDeleteLabel(label.id)}
        />
      ))}
    </div>
  );
};

export { LabelList };
