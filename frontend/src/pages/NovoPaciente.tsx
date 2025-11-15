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

interface NewPatientModalProps {
  onSuccess: () => void;
}

interface PatientForm {
  nome: string;
  cpf: string;
  email: string;
  telefone: string;
  dataNascimento: string;
  estado: string;
  cidade: string;
  bairro: string;
  responsavelNome: string;
  responsavelCpf: string;
  responsavelEmail: string;
  responsavelTelefone: string;
  responsavelDataNascimento: string;
}

const NewPatientModal = ({ onSuccess }: NewPatientModalProps) => {
  const [form, setForm] = useState<PatientForm>({
    nome: "",
    cpf: "",
    email: "",
    telefone: "",
    dataNascimento: "",
    estado: "",
    cidade: "",
    bairro: "",
    responsavelNome: "",
    responsavelCpf: "",
    responsavelEmail: "",
    responsavelTelefone: "",
    responsavelDataNascimento: "",
  });

  const [open, setOpen] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const isMinor = () => {
    if (!form.dataNascimento) return false;
    const birth = new Date(form.dataNascimento);
    const today = new Date();

    let age = today.getFullYear() - birth.getFullYear();
    const hasBirthdayPassed =
      today.getMonth() > birth.getMonth() ||
      (today.getMonth() === birth.getMonth() && today.getDate() >= birth.getDate());

    if (!hasBirthdayPassed) age--;
    return age < 18;
  };

  const savePatient = async () => {
    try {
      const token = localStorage.getItem("token");

      const payload: any = {
        nome: form.nome,
        cpf: form.cpf,
        email: form.email,
        telefone: form.telefone,
        dataNascimento: form.dataNascimento,
        estado: form.estado,
        cidade: form.cidade,
        bairro: form.bairro,
      };

      if (isMinor()) {
        payload.responsavel = {
          nome: form.responsavelNome,
          cpf: form.responsavelCpf,
          email: form.responsavelEmail,
          telefone: form.responsavelTelefone,
          dataNascimento: form.responsavelDataNascimento,
        };
      }

      const res = await fetch("http://localhost:8080/api/pacientes/criar", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) throw new Error("Erro ao salvar paciente");

      onSuccess();
      setOpen(false);
      // Resetar formulário ao fechar
      setForm({
        nome: "",
        cpf: "",
        email: "",
        telefone: "",
        dataNascimento: "",
        estado: "",
        cidade: "",
        bairro: "",
        responsavelNome: "",
        responsavelCpf: "",
        responsavelEmail: "",
        responsavelTelefone: "",
        responsavelDataNascimento: "",
      });
    } catch (error) {
      console.error("Erro:", error);
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="gap-2 bg-gradient-to-r from-primary to-secondary">
          <Plus className="w-4 h-4" />
          Novo Paciente
        </Button>
      </DialogTrigger>

      <DialogContent className="max-w-2xl">
        <DialogHeader>
          <DialogTitle className="text-xl font-semibold">Novo Paciente</DialogTitle>
        </DialogHeader>

        <div className="space-y-6 py-4">
          {/* --- DADOS DO PACIENTE --- */}
          <div className="space-y-3">
            <h3 className="font-semibold text-lg">Dados do Paciente</h3>
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-1">
                <Label>Nome</Label>
                <Input name="nome" value={form.nome} onChange={handleChange} />
              </div>
              <div className="space-y-1">
                <Label>CPF</Label>
                <Input name="cpf" value={form.cpf} onChange={handleChange} />
              </div>
              <div className="space-y-1">
                <Label>Email</Label>
                <Input name="email" value={form.email} onChange={handleChange} />
              </div>
              <div className="space-y-1">
                <Label>Telefone</Label>
                <Input name="telefone" value={form.telefone} onChange={handleChange} />
              </div>
              <div className="space-y-1">
                <Label>Data de Nascimento</Label>
                <Input
                  type="date"
                  name="dataNascimento"
                  value={form.dataNascimento}
                  onChange={handleChange}
                />
              </div>
              <div className="space-y-1">
                <Label>Estado</Label>
                <Input name="estado" value={form.estado} onChange={handleChange} />
              </div>
              <div className="space-y-1">
                <Label>Cidade</Label>
                <Input name="cidade" value={form.cidade} onChange={handleChange} />
              </div>
              <div className="space-y-1">
                <Label>Bairro</Label>
                <Input name="bairro" value={form.bairro} onChange={handleChange} />
              </div>
            </div>
          </div>

          {/* --- RESPONSÁVEL (somente se menor) --- */}
          {isMinor() && (
            <div className="space-y-3 pt-2 border-t">
              <h3 className="font-semibold text-lg">Responsável</h3>
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label>Nome</Label>
                  <Input
                    name="responsavelNome"
                    value={form.responsavelNome}
                    onChange={handleChange}
                  />
                </div>
                <div className="space-y-1">
                  <Label>CPF</Label>
                  <Input
                    name="responsavelCpf"
                    value={form.responsavelCpf}
                    onChange={handleChange}
                  />
                </div>
                <div className="space-y-1">
                  <Label>Email</Label>
                  <Input
                    name="responsavelEmail"
                    value={form.responsavelEmail}
                    onChange={handleChange}
                  />
                </div>
                <div className="space-y-1">
                  <Label>Telefone</Label>
                  <Input
                    name="responsavelTelefone"
                    value={form.responsavelTelefone}
                    onChange={handleChange}
                  />
                </div>
                <div className="space-y-1">
                  <Label>Data de Nascimento</Label>
                  <Input
                    type="date"
                    name="responsavelDataNascimento"
                    value={form.responsavelDataNascimento}
                    onChange={handleChange}
                  />
                </div>
              </div>
            </div>
          )}

          {/* BOTÃO */}
          <Button className="w-full py-3 text-base" onClick={savePatient}>
            Salvar Paciente
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default NewPatientModal;
