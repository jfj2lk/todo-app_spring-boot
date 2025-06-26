import { z } from "zod";

export type LabelType = {
  id: number;
  name: string;
  createdAt: string;
  updatedAt: string;
};

export const labelFormSchema = z.object({
  name: z.string().min(1, { message: "名前を入力してください。" }),
});

export const defaultLabelFormValues: z.infer<typeof labelFormSchema> = {
  name: "",
};
