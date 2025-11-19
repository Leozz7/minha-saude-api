import { useState, useEffect } from "react";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Plus } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

const NewProcedureModal = ({ onSuccess }: any) => {
  const [open, setOpen] = useState(false);
  const { toast } = useToast();

  const [form, setForm] = useState({
    nome: "",
    descricao: "",
    valorPlano: "",
    valorParticular: "",
  });

  const handleChange = (e: any) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const saveProcedure = async () => {
    try {
      const token = localStorage.getItem("token");

      const payload = {
        nome: form.nome,
        descricao: form.descricao,
        valorPlano: Number(form.valorPlano),
        valorParticular: Number(form.valorParticular),
      };

      const res = await fetch("http://localhost:8080/api/procedimentos/criar", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) throw new Error("Erro ao criar procedimento");

      onSuccess();
      setOpen(false);

      setForm({
        nome: "",
        descricao: "",
        valorPlano: "",
        valorParticular: "",
      });
      toast({
        title: "Procedimento criado",
        description: `Procedimento ${form.nome} criado com sucesso!`,
      })
    } catch (erro) {
      toast({
        title: "Erro ao registrar procedimento",
        description: "Verifique os dados e tente novamente",
        variant: "destructive",
      });
      console.error("Erro ao criar procedimento:", erro);
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="gap-2 bg-gradient-to-r from-primary to-secondary">
          <Plus className="w-4 h-4" />
          Novo Procedimento
        </Button>
      </DialogTrigger>

      <DialogContent className="max-w-lg">
        <DialogHeader>
          <DialogTitle>Novo Procedimento</DialogTitle>
        </DialogHeader>

        <div className="grid gap-4 py-4">

          <div className="grid grid-cols-4 items-center gap-4">
            <Label>Nome</Label>
            <Input
              name="nome"
              value={form.nome}
              onChange={handleChange}
              className="col-span-3"
              placeholder="Ex: Exame de Sangue"
            />
          </div>

          <div className="grid grid-cols-4 items-center gap-4">
            <Label>Descrição</Label>
            <Input
              name="descricao"
              value={form.descricao}
              onChange={handleChange}
              className="col-span-3"
              placeholder="Ex: Hemograma completo"
            />
          </div>

          <div className="grid grid-cols-4 items-center gap-4">
            <Label>Plano</Label>
            <Input
              name="valorPlano"
              value={form.valorPlano}
              onChange={handleChange}
              className="col-span-3"
              placeholder="Ex: 50.00"
            />
          </div>

          <div className="grid grid-cols-4 items-center gap-4">
            <Label>Particular</Label>
            <Input
              name="valorParticular"
              value={form.valorParticular}
              onChange={handleChange}
              className="col-span-3"
              placeholder="Ex: 120.00"
            />
          </div>

        </div>

        <Button className="w-full" onClick={saveProcedure}>
          Salvar
        </Button>
      </DialogContent>
    </Dialog>
  );
};

export default NewProcedureModal;
