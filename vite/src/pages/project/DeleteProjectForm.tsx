import { Button } from "@/components/ui/button";
import { ProjectType } from "@/types/project";
import { apiRequest } from "@/utils/api";
import { Dispatch, SetStateAction } from "react";
const DeleteProjectForm = (props: {
  projectId: number;
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
}) => {
  const handleDeleteProject = async (id: number) => {
    const json = await apiRequest<number>(
      `/api/projects/${props.projectId}`,
      "DELETE",
    );

    props.setProjects((prev) => prev.filter((e) => e.id !== json.data));
  };

  return (
    <Button
      onClick={() => {
        handleDeleteProject(props.projectId);
      }}
    >
      削除
    </Button>
  );
};

export default DeleteProjectForm;
