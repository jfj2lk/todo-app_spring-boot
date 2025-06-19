import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import { ProjectType } from "@/types/project";
import axios from "axios";
import { Trash2 } from "lucide-react";
import { Dispatch, SetStateAction } from "react";
const DeleteProjectForm = (props: {
  project: ProjectType;
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
}) => {
  const handleDeleteProject = (projectId: number) => {
    axios.delete(`/api/projects/${projectId}`).then((response) => {
      props.setProjects((prev) =>
        prev.filter((project) => project.id !== response.data.data),
      );
    });
  };

  return (
    <AlertDialog>
      <AlertDialogTrigger>
        <Button
          variant="ghost"
          size="icon"
          className="text-muted-foreground hover:text-foreground size-8"
        >
          <Trash2 className="size-4" />
        </Button>
      </AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>プロジェクトを削除しますか?</AlertDialogTitle>
          <AlertDialogDescription>
            {"プロジェクト名"}プロジェクトとその全てのTodoが全て削除されます。
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>キャンセル</AlertDialogCancel>
          <AlertDialogAction
            onClick={() => {
              handleDeleteProject(props.project.id);
            }}
          >
            削除
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
};

export default DeleteProjectForm;
