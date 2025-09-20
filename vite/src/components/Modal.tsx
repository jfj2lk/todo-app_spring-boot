import { ReactNode } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "./ui/dialog";

const Modal = (props: {
  trigger: ReactNode;
  title: ReactNode;
  content: ReactNode;
}) => {
  return (
    <Dialog>
      <DialogTrigger>{props.trigger}</DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>{props.title}</DialogTitle>
          <DialogDescription></DialogDescription>
        </DialogHeader>
        <div>{props.content}</div>
      </DialogContent>
    </Dialog>
  );
};

export default Modal;
