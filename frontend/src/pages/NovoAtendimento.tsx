import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";
import { useState } from "react";
import { useToast } from "@/hooks/use-toast";

interface NewAtendimentoModalProps {
  onSuccess: () => void;
}

interface AtendimentoForm {
  usuarioId: string;
  pacienteId: string;
  procedimentoIds: string;
  tipoPagamento: string;
  numeroCarteira: string | null;
  dataAtendimento: string;
}

const NewAtendimentoModal = ({ onSuccess }: NewAtendimentoModalProps) => {
  const { toast } = useToast();
  const [form, setForm] = useState<AtendimentoForm>({
    usuarioId: "",
    pacienteId: "",
    procedimentoIds: "",
    tipoPagamento: "",
    numeroCarteira: "",
    dataAtendimento: "",
  });

  const [open, setOpen] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const saveAtendimento = async () => {
    try {
      const token = localStorage.getItem("token");

      const payload = {
        usuarioId: Number(form.usuarioId),
        pacienteId: Number(form.pacienteId),
        procedimentoIds: form.procedimentoIds
          .split(",")
          .map((id) => Number(id.trim())),
        tipoPagamento: form.tipoPagamento,
        numeroCarteira: form.numeroCarteira || null,
        dataAtendimento: form.dataAtendimento,
      };

      const res = await fetch("http://localhost:8080/api/atendimentos/criar", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        throw new Error("Erro ao criar atendimento");
      };

      onSuccess();
      setOpen(false);

      // reset
      setForm({
        usuarioId: "",
        pacienteId: "",
        procedimentoIds: "",
        tipoPagamento: "",
        numeroCarteira: "",
        dataAtendimento: "",
      });
      toast({
        title: "Atendimento criado",
        description: `Atendimento criado com sucesso!`,
      });
    } catch (error) {
      toast({
        title: "Erro ao registrar:" + error,
        description: "Verifique os dados e tente novamente",
        variant: "destructive",
      });
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="gap-2 bg-gradient-to-r from-primary to-secondary">
          <Plus className="w-4 h-4" />
          Novo Atendimento
        </Button>
      </DialogTrigger>

      <DialogContent className="max-w-xl">
        <DialogHeader>
          <DialogTitle className="text-xl font-semibold">
            Criar Atendimento
          </DialogTitle>
        </DialogHeader>

        <div className="space-y-6 py-4">

          <div className="space-y-3">
            <h3 className="font-semibold text-lg">Dados</h3>

            <div className="grid grid-cols-2 gap-4">

              <div className="space-y-1">
                <Label>Usuário ID</Label>
                <Input name="usuarioId" value={form.usuarioId} onChange={handleChange} />
              </div>

              <div className="space-y-1">
                <Label>Paciente ID</Label>
                <Input name="pacienteId" value={form.pacienteId} onChange={handleChange} />
              </div>

              <div className="space-y-1">
                <Label>Procedimentos (ex: 1,3)</Label>
                <Input
                  name="procedimentoIds"
                  value={form.procedimentoIds}
                  onChange={handleChange}
                />
              </div>

              <div className="space-y-1">
                <Label>Tipo Pagamento</Label>
                <Input
                  placeholder="PARTICULAR / PLANO"
                  name="tipoPagamento"
                  value={form.tipoPagamento}
                  onChange={handleChange}
                />
              </div>

              <div className="space-y-1">
                <Label>N° Carteira (opcional)</Label>
                <Input
                  name="numeroCarteira"
                  value={form.numeroCarteira || ""}
                  onChange={handleChange}
                />
              </div>

              <div className="space-y-1">
                <Label>Data do Atendimento</Label>
                <Input
                  type="datetime-local"
                  name="dataAtendimento"
                  value={form.dataAtendimento}
                  onChange={handleChange}
                />
              </div>

            </div>
          </div>

          <Button className="w-full py-3 text-base" onClick={saveAtendimento}>
            Salvar Atendimento
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default NewAtendimentoModal;
