package hu.sze.orcciot.backends.c.STM32F4

import net.sf.orcc.df.Network

/**
 * Generate and print couples of traces file names.
 *  
 * @author Khaled JERBI
 * 
 */
class TracesPrinter extends CTemplate {

	def protected getTracesFileContent(Network network) '''
		«FOR connection : network.connections»
			«connection.target.label»_«connection.targetPort.name».txt «connection.source.label»_«connection.sourcePort.name».txt «connection.target.label» «connection.targetPort.name» «connection.source.label» «connection.sourcePort.name» «connection.safeSize»
		«ENDFOR»
	'''
}
