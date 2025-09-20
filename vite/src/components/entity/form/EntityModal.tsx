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
import {
  modeType,
  useEntityManagerPropsContext,
} from "../EntityManagerProvider";
import { EntityForm } from "./EntityForm";

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
  const dispatch = useAppDispatch();
  const { labelName, deleteEntity } = useEntityManagerPropsContext();

  const title = labelName + titleMap[props.mode];
  const description =
    props.mode === "DELETE"
      ? props.entity?.name + "は永久に削除されます。"
      : null;
  const submitText = submitTextMap[props.mode];

  // Dialogの開閉を制御するstate
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);

  return (
    <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
      <DialogTrigger asChild>{props.children}</DialogTrigger>
      <DialogContent showCloseButton={false}>
        <DialogHeader>
          <DialogTitle>{title}</DialogTitle>
          <DialogDescription>{description}</DialogDescription>
        </DialogHeader>

        {/* modeが作成または更新の場合 */}
        {(props.mode === "CREATE" || props.mode === "UPDATE") && (
          <EntityForm
            mode={props.mode}
            entity={props.entity}
            submitText={submitText}
            setDialogOpen={setDialogOpen}
          />
        )}

        {/* modeが削除の場合 */}
        {props.mode === "DELETE" && props.entity && (
          <DialogFooter>
            <DialogClose asChild>
              <Button variant={"secondary"}>キャンセル</Button>
            </DialogClose>
            <DialogClose asChild>
              <Button
                type="submit"
                onClick={() => {
                  deleteEntity && dispatch(deleteEntity(props.entity!.id));
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
