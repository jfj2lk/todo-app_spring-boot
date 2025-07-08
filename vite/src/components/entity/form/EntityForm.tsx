import { Button } from "@/components/ui/button";
import { DialogClose, DialogFooter } from "@/components/ui/dialog";
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
import { useAppDispatch } from "@/store";
import { zodResolver } from "@hookform/resolvers/zod";
import { Dispatch, SetStateAction } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import {
  modeType,
  useEntityManagerPropsContext,
} from "../EntityManagerProvider";

const EntityForm = (props: {
  mode: modeType;
  entity?: any;
  submitText: string;
  setDialogOpen: Dispatch<SetStateAction<boolean>>;
}) => {
  const dispatch = useAppDispatch();
  const { formSchema, createEntity, updateEntity, defaultFormValues } =
    useEntityManagerPropsContext();

  // フォームスキーマからキーのみを抽出
  const formKeys = Object.keys(formSchema.shape);

  // フォームスキーマ
  const form = useForm<z.infer<typeof formSchema>>({
    mode: "onChange",
    resolver: zodResolver(formSchema),
    // entityが存在する場合はそれをデフォルト値にし、存在しない場合はフォームのデフォルト値を使用する
    defaultValues: props.entity ?? defaultFormValues,
  });

  // フォーム送信時処理
  const onSubmit = (values: z.infer<typeof formSchema>) => {
    if (props.mode === "CREATE") {
      createEntity && dispatch(createEntity(values));
    } else if (props.mode === "UPDATE") {
      updateEntity && dispatch(updateEntity({ id: props.entity.id, values }));
    }
    props.setDialogOpen(false);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        {formKeys.map((key) => (
          <FormField
            key={key}
            control={form.control}
            name={String(key)}
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
        ))}

        <DialogFooter>
          <DialogClose asChild>
            <Button variant={"secondary"}>キャンセル</Button>
          </DialogClose>
          <Button type="submit">{props.submitText}</Button>
        </DialogFooter>
      </form>
    </Form>
  );
};

export { EntityForm };
