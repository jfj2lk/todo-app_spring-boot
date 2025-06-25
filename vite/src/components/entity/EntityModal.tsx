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

import { useAppDispatch } from "@/store";
import { ReactNode, useState } from "react";
import { EntityForm } from "./EntityForm";
import { useEntityManagerPropsContext } from "./logic/entity-context";
import { modeType } from "./logic/entity-type";

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
  entity?: any;
}) => {
  const { entityName, deleteEntity } = useEntityManagerPropsContext();

  // Dialogの開閉を制御するstate
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);

  const title = entityName + titleMap[props.mode];
  let description;
  if (props.mode === "DELETE") {
    description = props.entity?.name + "は永久に削除されます。";
  }
  const submitText = submitTextMap[props.mode];

  const dispatch = useAppDispatch();

  return (
    <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
      <DialogTrigger asChild>{props.children}</DialogTrigger>
      <DialogContent showCloseButton={false}>
        <DialogHeader>
          <DialogTitle>{title}</DialogTitle>
          <DialogDescription>{description}</DialogDescription>
        </DialogHeader>

        {props.mode !== "DELETE" && props.entity && (
          <main>
            <EntityForm
              mode={props.mode}
              entity={props.entity}
              submitText={submitText}
              setDialogOpen={setDialogOpen}
            />
          </main>
        )}

        {props.mode === "DELETE" && props.entity && (
          <DialogFooter>
            <DialogClose asChild>
              <Button variant={"secondary"}>キャンセル</Button>
            </DialogClose>
            <DialogClose asChild>
              <Button
                type="submit"
                onClick={() => {
                  dispatch(deleteEntity(props.entity!.id));
                }}
              >
                {submitText}
              </Button>
            </DialogClose>
          </DialogFooter>
        )}
      </DialogContent>
    </Dialog>
  );
};

export { EntityModal };
