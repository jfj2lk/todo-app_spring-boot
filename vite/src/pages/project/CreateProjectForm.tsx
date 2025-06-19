import Modal from "@/components/Modal";
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
import { Plus } from "lucide-react";
import { Dispatch, SetStateAction } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const CreateProjectForm = (props: {
  projects: ProjectType[];
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
}) => {
  const formSchema = z.object({
    name: z
      .string()
      .min(1, { message: "プロジェクト名を入力してください。" })
      .max(50, { message: "プロジェクト名は50文字以下にしてください。" }),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
    },
  });

  const handleCreateProject = (values: z.infer<typeof formSchema>) => {
    axios.post("/api/projects", values).then((response) => {
      props.setProjects((prev) => [...prev, response.data.data]);
      form.reset();
    });
  };

  return (
    <div>
      <Modal
        trigger={
          <Button
            variant="ghost"
            size="icon"
            className="text-muted-foreground hover:text-foreground size-8"
          >
            <Plus className="size-4" />
          </Button>
        }
        title={"プロジェクトを追加"}
        content={
          <Form {...form}>
            <form onSubmit={form.handleSubmit(handleCreateProject)}>
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
        }
      />
    </div>
  );
};

export default CreateProjectForm;
