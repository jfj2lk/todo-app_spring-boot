import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { ProjectType } from "@/types/project";
import { apiRequest } from "@/utils/api";
import { zodResolver } from "@hookform/resolvers/zod";
import { Dispatch, SetStateAction } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const CreateProjectForm = (props: {
  projects: ProjectType[];
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
}) => {
  const formSchema = z.object({
    name: z.string().min(1, { message: "プロジェクト名を入力してください。" }),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
    },
  });

  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    const json = await apiRequest<ProjectType>(`/api/projects`, "POST", values);
    props.setProjects((prev) => [...prev, json.data]);
    form.reset();
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="flex">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem className="flex">
              <FormControl>
                <Input placeholder="プロジェクト名" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">作成</Button>
      </form>
    </Form>
  );
};

export default CreateProjectForm;
