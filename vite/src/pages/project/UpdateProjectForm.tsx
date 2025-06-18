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
import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios";
import { Dispatch, SetStateAction } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const UpdateProjectForm = (props: {
  project: ProjectType;
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
  setEditingId: Dispatch<SetStateAction<number | null>>;
}) => {
  const formSchema = z.object({
    name: z.string().min(1, { message: "プロジェクト名を入力してください。" }),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: props.project,
  });

  const onSubmit = (values: z.infer<typeof formSchema>) => {
    axios
      .patch(`/api/projects/${props.project.id}`, values)
      .then((response) => {
        const payload = response.data.data;
        props.setProjects((prev) =>
          prev.map((project) =>
            project.id === payload.id ? payload : project,
          ),
        );
      });

    props.setEditingId(null);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
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

        <Button type="submit">更新</Button>
      </form>
    </Form>
  );
};

export default UpdateProjectForm;
