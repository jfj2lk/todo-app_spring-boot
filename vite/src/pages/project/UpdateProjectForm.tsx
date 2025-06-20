import Modal from "@/components/Modal";
import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
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
import { Pencil } from "lucide-react";
import { Dispatch, SetStateAction } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const UpdateProjectForm = (props: {
  project: ProjectType;
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
}) => {
  const formSchema = z.object({
    name: z.string().min(1, { message: "プロジェクト名を入力してください。" }),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: props.project,
  });

  const handleUpdateProject = (
    projectId: number,
    values: z.infer<typeof formSchema>,
  ) => {
    axios.patch(`/api/projects/${projectId}`, values).then((response) => {
      const data = response.data.data;
      props.setProjects((prev) =>
        prev.map((project) => (project.id === data.id ? data : project)),
      );
    });
  };

  return (
    <Modal
      trigger={
        <Button
          variant="ghost"
          size="icon"
          className="text-muted-foreground hover:text-foreground size-8"
        >
          <Pencil className="size-4" />
        </Button>
      }
      title={"プロジェクトを編集"}
      content={
        <Form {...form}>
          <form
            onSubmit={form.handleSubmit((values) => {
              handleUpdateProject(props.project.id, values);
            })}
          >
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

            <DialogClose>
              <Button type="submit">更新</Button>
            </DialogClose>
          </form>
        </Form>
      }
    />
  );
};

export default UpdateProjectForm;
