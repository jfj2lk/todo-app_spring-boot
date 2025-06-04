"use client";

import type React from "react";

import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { LabelType as LabelType } from "@/types/label";

interface LabelModalProps {
  isOpen: boolean;
  onClose: () => void;
  handleAddLabel: (addLabel: { name: string }) => void;
  handleUpdateLabel: (
    updateLabelId: number,
    updateLabel: { name: string },
  ) => void;
  editingLabel: LabelType | null;
}

// const colorOptions = [
//   { value: "gray", label: "グレー", class: "bg-gray-500" },
//   { value: "blue", label: "ブルー", class: "bg-blue-500" },
//   { value: "red", label: "レッド", class: "bg-red-500" },
//   { value: "green", label: "グリーン", class: "bg-green-500" },
//   { value: "yellow", label: "イエロー", class: "bg-yellow-500" },
//   { value: "purple", label: "パープル", class: "bg-purple-500" },
// ];

const LabelModal = ({
  isOpen,
  onClose,
  handleAddLabel,
  handleUpdateLabel,
  editingLabel,
}: LabelModalProps) => {
  const [name, setName] = useState("");
  const [color, setColor] = useState("gray");

  useEffect(() => {
    if (editingLabel) {
      setName(editingLabel.name);
      // setColor(editingLabel.color);
      setColor("gray");
    } else {
      setName("");
      setColor("gray");
    }
  }, [editingLabel, isOpen]);

  const handleSave = () => {
    const trimName = name.trim();

    if (trimName) {
      editingLabel
        ? handleUpdateLabel(editingLabel.id, { name: trimName })
        : handleAddLabel({ name: trimName });
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      handleSave();
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <div className="flex items-center justify-between">
            <DialogTitle className="flex items-center gap-2">
              {editingLabel ? "ラベルを編集" : "ラベルを追加"}
              {/* ツールチップ */}
              {/* <TooltipProvider>
                <Tooltip>
                  <TooltipTrigger>
                    <HelpCircle className="text-muted-foreground h-4 w-4" />
                  </TooltipTrigger>
                  <TooltipContent>
                    <p>ラベルは最大60文字まで入力できます</p>
                  </TooltipContent>
                </Tooltip>
              </TooltipProvider> */}
            </DialogTitle>
            {/* 閉じるボタン */}
            {/* <Button
              variant="ghost"
              size="icon"
              onClick={onClose}
              className="h-6 w-6"
            >
              <X className="h-4 w-4" />
            </Button> */}
          </div>
        </DialogHeader>

        <div className="space-y-6 py-4">
          <div className="space-y-2">
            <Label htmlFor="name">名前</Label>
            <div className="space-y-1">
              <Input
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                onKeyDown={handleKeyDown}
                maxLength={60}
                placeholder="ラベル名を入力"
                className="w-full"
              />
              <div className="text-muted-foreground text-right text-xs">
                {name.length}/60
              </div>
            </div>
          </div>

          {/* 色選択欄 */}
          {/* <div className="space-y-3">
            <Label>カラー</Label>
            <RadioGroup value={color} onValueChange={setColor}>
              <div className="grid grid-cols-3 gap-3">
                {colorOptions.map((option) => (
                  <div
                    key={option.value}
                    className="flex items-center space-x-2"
                  >
                    <RadioGroupItem value={option.value} id={option.value} />
                    <Label
                      htmlFor={option.value}
                      className="flex cursor-pointer items-center gap-2"
                    >
                      <div className={`h-3 w-3 rounded-full ${option.class}`} />
                      <span className="text-sm">{option.label}</span>
                    </Label>
                  </div>
                ))}
              </div>
            </RadioGroup>
          </div> */}
        </div>

        <div className="flex justify-end gap-3 pt-4">
          <Button variant="outline" onClick={onClose}>
            キャンセル
          </Button>
          <Button onClick={handleSave} disabled={!name.trim()}>
            {editingLabel ? "更新" : "追加"}
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export { LabelModal };
