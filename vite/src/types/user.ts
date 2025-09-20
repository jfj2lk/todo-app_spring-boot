import { z } from "zod";

export type UserType = {
  id: number;
  name: string;
  email: string;
};

export const userFormSchema = z.object({
  name: z.string().min(1, { message: "名前を入力してください。" }),
  email: z.string().min(1, { message: "メールアドレスを入力してください。" }),
  password: z.string().min(1, { message: "パスワードを入力してください。" }),
});

export const defaultUserFormValues: z.infer<typeof userFormSchema> = {
  name: "",
  email: "",
  password: "",
};
