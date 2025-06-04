"use client";

import { useState } from "react";
import { Edit2, MoreHorizontal, Tag, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Label } from "@/types/label";

interface LabelItemProps {
  label: Label;
  onEdit: () => void;
  onDelete: () => void;
}

// const colorMap = {
//   gray: "text-gray-500",
//   blue: "text-blue-500",
//   red: "text-red-500",
//   green: "text-green-500",
//   yellow: "text-yellow-500",
//   purple: "text-purple-500",
// };

const LabelItem = ({ label, onEdit, onDelete }: LabelItemProps) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div
      className="hover:bg-muted/50 group flex items-center justify-between rounded-lg px-4 py-3 transition-colors"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div className="flex items-center gap-3">
        <Tag className={`h-4 w-4 text-gray-500`} />
        {/* <Tag
          className={`h-4 w-4 ${colorMap[label.color as keyof typeof colorMap] || "text-gray-500"}`}
        /> */}
        <span className="text-sm font-medium">{label.name}</span>
      </div>

      <div
        className={`flex items-center gap-1 transition-opacity ${isHovered ? "opacity-100" : "opacity-0"}`}
      >
        {/* 編集ボタン */}
        <Button
          variant="ghost"
          size="icon"
          onClick={onEdit}
          className="text-muted-foreground hover:text-foreground h-8 w-8"
        >
          <Edit2 className="h-3 w-3" />
        </Button>

        {/* 削除ボタン */}
        <Button
          variant="ghost"
          size="icon"
          onClick={onDelete}
          className="text-muted-foreground hover:text-foreground h-8 w-8"
        >
          <Trash2 className="mr-2 h-4 w-4" />
        </Button>

        {/* <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button
              variant="ghost"
              size="icon"
              className="text-muted-foreground hover:text-foreground h-8 w-8"
            >
              <MoreHorizontal className="h-3 w-3" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuItem onClick={onEdit}>
              <Edit2 className="mr-2 h-4 w-4" />
              編集
            </DropdownMenuItem>
            <DropdownMenuItem onClick={onDelete} className="text-destructive">
              <Trash2 className="mr-2 h-4 w-4" />
              削除
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu> */}
      </div>
    </div>
  );
};

export { LabelItem };
