package com.orionsoftware.service.impl;

import com.orionsoftware.dto.ConsultaListaExamenDTO;
import com.orionsoftware.dto.ConsultaResumenDTO;
import com.orionsoftware.dto.FiltroConsultaDTO;
import com.orionsoftware.model.Consulta;
import com.orionsoftware.repo.IConsultaExamenRepo;
import com.orionsoftware.repo.IConsultaRepo;
import com.orionsoftware.service.IConsultaService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IConsultaServiceImpl implements IConsultaService {

    @Autowired
    private IConsultaRepo repo;

    @Autowired
    private IConsultaExamenRepo ceRepo;

    @Override
    public Consulta registrar(Consulta obj) {
        /*Recorro los detalles y le asigno(setConsulta) la misma consulta con la que viene*/
        obj.getDetalleConsulta().forEach(detalleConsulta -> detalleConsulta.setConsulta(obj));
        return repo.save(obj);
    }

    @Override
    public Consulta modificar(Consulta obj) {
        return repo.save(obj);
    }

    @Override
    public Consulta leerPorId(Integer id) {
        return repo.findOne(id);
    }

    @Override
    public List<Consulta> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        repo.delete(id);
    }

    @Transactional
    @Override
    public Consulta registrarTransaccional(ConsultaListaExamenDTO consultaDTO) {
        consultaDTO.getConsulta().getDetalleConsulta().forEach(System.out::println);

        consultaDTO.getConsulta().getDetalleConsulta().forEach(detalleConsulta -> detalleConsulta.setConsulta(consultaDTO.getConsulta()));
        repo.save(consultaDTO.getConsulta());

        consultaDTO.getListaExamen().forEach(ex -> System.out.println(ex.getIdExamen()+" "+ex.getDescripcion()));
        consultaDTO.getListaExamen().forEach(ex -> ceRepo.registrar(consultaDTO.getConsulta().getIdConsulta(), ex.getIdExamen()));
        return consultaDTO.getConsulta();
    }

    @Override
    public List<Consulta> buscar(FiltroConsultaDTO filtro) {
        //Buscamos la consultas segun el documento y el nombre recibido
        return repo.buscar(filtro.getDocumento(), filtro.getNombreCompleto());
    }

    @Override
    public List<Consulta> buscarByFecha(FiltroConsultaDTO filtro) {
        LocalDateTime fechaSiguiente = filtro.getFechaConsulta().plusDays(1);
        return repo.buscarFecha(filtro.getFechaConsulta(), fechaSiguiente);
    }

    @Override
    public List<ConsultaResumenDTO> listarResumen() {
        List<ConsultaResumenDTO> consultas = new ArrayList<>();
        // List<Object[]>
        // cantidad fecha
        // [4 , 11/05/2019]
        // [1 , 18/05/2019]
        repo.listarResumen().forEach( x -> {
            ConsultaResumenDTO cr = new ConsultaResumenDTO();
            cr.setCantidad(Integer.parseInt(String.valueOf(x[0])));
            cr.setFecha(String.valueOf(x[1]));
            consultas.add(cr);
        });
        return consultas;
    }

    @Override
    public byte[] generarReporte()  {
        byte[] data = null;
        try {
            File fileReport = new ClassPathResource("/reports/consultas.jasper").getFile();
            JasperPrint print = JasperFillManager.fillReport(fileReport.getPath(), null, new JRBeanCollectionDataSource(this.listarResumen()));
            data = JasperExportManager.exportReportToPdf(print);
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
