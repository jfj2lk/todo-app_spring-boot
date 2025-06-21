import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

import { ReactNode } from "react";
import { EntityForm } from "./EntityForm";
import { EntityType } from "./EntityManager";

export type modeType = "CREATE" | "UPDATE" | "DELETE";

// modeに対応するタイトル
const titleMap = {
  CREATE: "を追加",
  UPDATE: "を編集",
  DELETE: "を削除しますか？",
};

// modeに対応する送信ボタンのテキスト
const submitTextMap = {
  CREATE: "追加",
  UPDATE: "保存",
  DELETE: "削除",
};

const EntityModal = (props: {
  children: ReactNode;
  mode: modeType;
  entity?: EntityType;
  entityName: string;
  main: ReactNode;
}) => {
  const title = props.entityName + titleMap[props.mode];
  let description;
  if (props.mode === "DELETE") {
    description = props.entity?.name + "は永久に削除されます。";
  }
  const submitText = submitTextMap[props.mode];

  return (
    <Dialog>
      <DialogTrigger asChild>{props.children}</DialogTrigger>
      <DialogContent showCloseButton={false}>
        <DialogHeader>
          <DialogTitle>{title}</DialogTitle>
          <DialogDescription>{description}</DialogDescription>
        </DialogHeader>

        {props.mode !== "DELETE" && props.entity && (
          <main>
            <EntityForm entity={props.entity} submitText={submitText} />
          </main>
        )}

        {props.mode === "DELETE" && props.entity && (
          <DialogFooter>
            <DialogClose asChild>
              <Button variant={"secondary"}>キャンセル</Button>
            </DialogClose>
            <DialogClose asChild>
              <Button type="submit">{submitText}</Button>
            </DialogClose>
          </DialogFooter>
        )}
      </DialogContent>
    </Dialog>
  );
};

export { EntityModal };
