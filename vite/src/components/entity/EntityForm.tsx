import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "../ui/button";
import { DialogClose, DialogFooter } from "../ui/dialog";
import { EntityType } from "./EntityManager";

const EntityForm = (props: { entity: EntityType; submitText: string }) => {
  // entityからidプロパティを取り除いたオブジェクトを作成する
  const { id, ...entityWithoutId } = props.entity;

  // フォーム定義
  const formSchema = z.object({
    name: z.string().min(1, { message: "名前を入力してください。" }),
    description: z.string(),
  });

  // フォーム設定
  const form = useForm<z.infer<typeof formSchema>>({
    mode: "onChange",
    resolver: zodResolver(formSchema),
    defaultValues: entityWithoutId,
  });

  // フォーム送信時処理
  const onSubmit = (values: z.infer<typeof formSchema>) => {
    console.log(values);
  };

  //   エンティティのキー定義
  type EntityKey = keyof z.infer<typeof formSchema>;

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        {(Object.entries(entityWithoutId) as [EntityKey, string][]).map(
          ([key, value]) => (
            <FormField
              key={key}
              control={form.control}
              name={key}
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{key}</FormLabel>
                  <FormControl>
                    <Input placeholder="" {...field} />
                  </FormControl>
                  <FormDescription></FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          ),
        )}
        <DialogFooter>
          <DialogClose asChild>
            <Button variant={"secondary"}>キャンセル</Button>
          </DialogClose>
          <DialogClose asChild>
            <Button type="submit">{props.submitText}</Button>
          </DialogClose>
        </DialogFooter>
      </form>
    </Form>
  );
};

export { EntityForm };
