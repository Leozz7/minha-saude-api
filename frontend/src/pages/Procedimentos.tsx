import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import NewProcedureModal from "./NovoProcedimento";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Plus, Search, Edit, Trash2 } from "lucide-react";

const Procedimentos = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [procedimento, setprocedimento] = useState([]);

  const loadprocedimento = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await fetch("http://localhost:8080/api/procedimentos/listar", {
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Erro ao carregar procedimentos");
      }

      const data = await response.json();
      setprocedimento(data.content);
    } catch (error) {
      console.error("Erro ao carregar procedimentos:", error);
    }
  };

  useEffect(() => {
    loadprocedimento();
  }, []);

  const filtered = procedimento.filter((p) =>
    p.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="space-y-6 animate-fade-in">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 animate-slide-in">
        <div>
          <h1 className="text-4xl font-bold tracking-tight font-display bg-clip-text text-transparent bg-gradient-to-r from-foreground to-foreground/70">
            Procedimentos
          </h1>
          <p className="text-muted-foreground mt-2 text-lg">
            Gerencie os procedimentos e valores
          </p>
        </div>
        <NewProcedureModal onSuccess={loadprocedimento} />
      </div>

      <Card className="border-border/50">
        <CardHeader className="border-b border-border/50">
          <CardTitle className="text-xl font-display">Lista de Procedimentos</CardTitle>
        </CardHeader>

        <CardContent>
          <div className="mb-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
              <Input
                placeholder="Buscar procedimento..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>
          </div>

          <div className="rounded-md border">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Id</TableHead>
                  <TableHead>Nome</TableHead>
                  <TableHead>Descrição</TableHead>
                  <TableHead>Preço Plano</TableHead>
                  <TableHead>Preço Particular</TableHead>
                </TableRow>
              </TableHeader>

              <TableBody>
                {filtered.map((p) => (
                  <TableRow key={p.id}>
                    <TableCell>{p.id}</TableCell>
                    <TableCell className="font-medium">{p.nome}</TableCell>
                    <TableCell className="text-muted-foreground">{p.descricao}</TableCell>
                    <TableCell className="font-medium">R$ {p.valorPlano}</TableCell>
                    <TableCell className="font-medium">R$ {p.valorParticular}</TableCell>

                  </TableRow>
                ))}
              </TableBody>
              {filtered.length === 0 && (
                  <TableRow>
                    <TableCell colSpan={8} className="text-center py-6 text-muted-foreground">
                      Nenhum procedimento encontrado
                    </TableCell>
                  </TableRow>
                )}
            </Table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Procedimentos;
