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
import { overrideByKeys } from "@/utils/utils";
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

  //   フォームスキーマからキーのみを抽出
  type formSchemaKeys = keyof z.infer<typeof formSchema>;

  // フォームのデフォルト値
  const defaultValues = props.entity
    ? overrideByKeys(defaultFormValues, props.entity)
    : defaultFormValues;

  // フォームスキーマ
  const form = useForm<z.infer<typeof formSchema>>({
    mode: "onChange",
    resolver: zodResolver(formSchema),
    defaultValues,
  });

  // フォーム送信時処理
  const onSubmit = (values: z.infer<typeof formSchema>) => {
    if (props.mode === "CREATE") {
      dispatch(createEntity(values));
    } else if (props.mode === "UPDATE") {
      dispatch(updateEntity({ id: props.entity.id, values }));
    }
    props.setDialogOpen(false);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        {(Object.entries(defaultValues) as [formSchemaKeys, string][]).map(
          ([key]) => (
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
          ),
        )}

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
