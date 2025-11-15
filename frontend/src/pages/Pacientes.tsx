import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import NewPatientModal from "./NovoPaciente";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { Plus, Search, Edit, Trash2 } from "lucide-react";

const Patients = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [patients, setPatients] = useState([]);

  // 🔥 Busca da API
  const fetchPatients = async () => {
    try {
      const token = localStorage.getItem("token");

      if (!token) {
        console.error("Nenhum token encontrado. Usuário não autenticado.");
        return;
      }

      const response = await fetch("http://localhost:8080/api/pacientes/listar", {
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Erro ao buscar pacientes");
      }

      const data = await response.json();
      setPatients(data.content);

    } catch (error) {
      console.error("Erro ao buscar pacientes:", error);
    }
  };

  useEffect(() => {
    fetchPatients();
  }, []);

  const filteredPatients = patients.filter((p: any) =>
    p.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const checkIsMinor = (birthDateString: string) => {
    const birthDate = new Date(birthDateString);
    const today = new Date();
    const age = today.getFullYear() - birthDate.getFullYear();
    return age < 18;
  };

  return (
    <div className="space-y-6 animate-fade-in">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 animate-slide-in">
        <div>
          <h1 className="text-4xl font-bold tracking-tight font-display bg-clip-text text-transparent bg-gradient-to-r from-foreground to-foreground/70">
            Pacientes
          </h1>
          <p className="text-muted-foreground mt-2 text-lg">
            Gerencie os pacientes da clínica
          </p>
        </div>
        <NewPatientModal onSuccess={() => fetchPatients()} />
      </div>

      <Card className="glass-card border-border/50 animate-fade-in-up" style={{ animationDelay: "0.1s" }}>
        <CardHeader>
          <CardTitle>Lista de Pacientes</CardTitle>
        </CardHeader>

        <CardContent>
          <div className="mb-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
              <Input
                placeholder="Buscar por nome"
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
                  <TableHead>Nome</TableHead>
                  <TableHead>CPF</TableHead>
                  <TableHead>Email</TableHead>
                  <TableHead>Telefone</TableHead>
                  <TableHead>Data Nasc.</TableHead>
                  <TableHead>Status</TableHead>
                  <TableHead>Responsável</TableHead>
                </TableRow>
              </TableHeader>

              <TableBody>
                {filteredPatients.map((patient: any) => {
                  const isMinor = checkIsMinor(patient.dataNascimento);

                  return (
                    <TableRow key={patient.id}>
                      <TableCell className="font-medium">{patient.nome}</TableCell>
                      <TableCell>{patient.cpf}</TableCell>
                      <TableCell>{patient.email}</TableCell>
                      <TableCell>{patient.telefone}</TableCell>
                      <TableCell>
                        {new Date(patient.dataNascimento).toLocaleDateString("pt-BR")}
                      </TableCell>

                      <TableCell>
                        {isMinor ? (
                          <Badge variant="secondary">Menor de Idade</Badge>
                        ) : (
                          <Badge variant="outline">Maior de Idade</Badge>
                        )}
                      </TableCell>

                      <TableCell>
                        {isMinor && patient.responsavel ? (
                          <span className="text-sm text-muted-foreground">
                            {patient.responsavel.nome}
                          </span>
                        ) : (
                          <span className="text-muted-foreground">—</span>
                        )}
                      </TableCell>

                  
                    </TableRow>
                  );
                })}

                {filteredPatients.length === 0 && (
                  <TableRow>
                    <TableCell colSpan={8} className="text-center py-6 text-muted-foreground">
                      Nenhum paciente encontrado.
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Patients;
