import { Button } from "@/components/ui/button";
import { ProjectType } from "@/types/project";
import axios from "axios";
import { Dispatch, SetStateAction } from "react";
const DeleteProjectForm = (props: {
  projectId: number;
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
