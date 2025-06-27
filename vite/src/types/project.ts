import { z } from "zod";

export type ProjectType = {
  id: number;
  name: string;
};

export const projectFormSchema = z.object({
  name: z.string().min(1, { message: "名前を入力してください。" }),
});

export const defaultProjectFormValues: z.infer<typeof projectFormSchema> = {
  name: "",
};
