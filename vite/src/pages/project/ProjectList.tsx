import { ProjectType } from "@/types/project";
import { Dispatch, SetStateAction, useState } from "react";
import DeleteProjectForm from "./DeleteProjectForm";
import UpdateProjectForm from "./UpdateProjectForm";

const ProjectList = (props: {
  projects: ProjectType[];
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
}) => {
  const [editingId, setEditingId] = useState<number | null>(null);

  return (
    <div>
      {props.projects.map((project) => (
        <div key={project.id}>
          {project.id === editingId ? (
            <div>
              <UpdateProjectForm
                project={project}
                setProjects={props.setProjects}
                setEditingId={setEditingId}
              />
              <DeleteProjectForm
                projectId={project.id}
                setProjects={props.setProjects}
              />
            </div>
          ) : (
            <div>
              <h3 onClick={() => setEditingId(project.id)}>{project.name}</h3>
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default ProjectList;
