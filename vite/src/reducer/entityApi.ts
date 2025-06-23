import { EntityType } from "@/components/entity/EntityManager";
import { createAsyncThunk } from "@reduxjs/toolkit";
import { z } from "zod";

const entityDatas: EntityType[] = [
  { id: 1, name: "entity1", description: "desc1" },
  { id: 2, name: "entity2", description: "desc2" },
  { id: 3, name: "entity3", description: "desc3" },
];

// フォーム定義
const formSchema = z.object({
  name: z.string().min(1, { message: "名前を入力してください。" }),
  description: z.string(),
});

export const getAllEntities = createAsyncThunk("getAllEntities", () => {
  console.log("getAll");
  return entityDatas;
});

// 作成処理
export const createEntity = createAsyncThunk(
  "createEntity",
  (values: z.infer<typeof formSchema>) => {
    console.log("create");
    const createdEntity = { id: 0, ...values };
    return createdEntity;
  },
);

// 更新処理
export const updateEntity = createAsyncThunk(
  "updateEntity",
  (payload: { entityId: number; values: z.infer<typeof formSchema> }) => {
    console.log("update");
    const updatedEntity = { id: payload.entityId, ...payload.values };
    return updatedEntity;
  },
);

// 削除処理
export const deleteEntity = createAsyncThunk(
  "deleteEntity",
  (deleteEntityId: number) => {
    console.log("delete");
    return deleteEntityId;
  },
);
