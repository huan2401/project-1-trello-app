import React, { useState } from "react";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import Column from "./Column";
import { TestWrapper } from "./CustomStyled";

const abc = [
  {
    id: "gary",
    name: "Gary Goodspeed",
  },
  {
    id: "gary1",
    name: "Gary Goodspeed1",
  },
  {
    id: "gary12",
    name: "Gary Goodspeed12",
  },
  {
    id: "gary123",
    name: "Gary Goodspeed123",
  },
];
const abc2 = [
  {
    id: "gary",
    name: "Garyyy Goodspeed",
  },
  {
    id: "gary1",
    name: "Garyyyy Goodspeed1",
  },
  {
    id: "gary12",
    name: "Garyyyy Goodspeed12",
  },
  {
    id: "gary123",
    name: "Garyyyy Goodspeed123",
  },
];
const abc1 = [
  {
    id: "gary",
    name: "Garyy Goodspeed",
  },
  {
    id: "gary1",
    name: "Garyy Goodspeed1",
  },
  {
    id: "gary12",
    name: "Garyy Goodspeed12",
  },
  {
    id: "gary123",
    name: "Garyy Goodspeed123",
  },
];
const finalSpaceCharacters = [
  {
    id: "abc",
    element: abc,
  },
  {
    id: "abc1",
    element: abc1,
  },
  {
    id: "abc2",
    element: abc2,
  },
];
const initData = {
  tasks: {
    "task-1": { id: "task-1", content: "I am task 1" },
    "task-2": { id: "task-2", content: "I am task 2" },
    "task-3": { id: "task-3", content: "I am task 3" },
    "task-4": { id: "task-4", content: "I am task 4" },
  },
  columns: {
    "column-1": {
      id: "column-1",
      title: "Todo",
      taskIds: ["task-1", "task-2", "task-3", "task-4"],
    },
    "column-2": {
      id: "column-2",
      title: "In Progress",
      taskIds: [],
    },
    // "column-3": {
    //   id: "column-3",
    //   title: "Done",
    //   taskIds: []
    // }
  },
  // columnOrder: ["column-1", "column-2", "column-3"]
  columnOrder: ["column-1", "column-2"],
};

const Test = () => {
  const [characters, updateCharacters] = useState(finalSpaceCharacters);
  const [starter, setStarter] = useState(initData);
  const handleOnDragEnd = (result) => {
    console.log("result", result);
    // if (!result.destination) return;
    // const items = Array.from(characters);
    // const [reorderedItem] = items.splice(result.source.index, 1);
    // console.log("reordered", reorderedItem);
    // items.splice(result.destination.index, 0, reorderedItem);

    // updateCharacters(items);
  };
  // return (
  //   <TestWrapper>
  //     <DragDropContext onDragEnd={handleOnDragEnd}>
  //       <Droppable
  //         droppableId="characters"
  //         type="COLUMN"
  //         direction="horizontal"
  //       >
  //         {(provided) => (
  //           <div
  //             className="characters"
  //             // style={{ listStyle: "none" }}
  //             {...provided.droppableProps}
  //             ref={provided.innerRef}
  //           >
  //             {characters.map(({ id, element }, index) => {
  //               return (
  //                 <Draggable key={id} draggableId={id} index={index}>
  //                   {(provided) => (
  //                     <div
  //                       key={index}
  //                       ref={provided.innerRef}
  //                       {...provided.draggableProps}
  //                       {...provided.dragHandleProps}
  //                     >
  //                       {element.map(({ id, name }, index) => {
  //                         return (
  //                           // <Draggable key={id} draggableId={id} index={index}>
  //                           //   {(provided) => (
  //                           <div
  //                             key={index}
  //                             {...provided.draggableProps}
  //                             {...provided.dragHandleProps}
  //                           >
  //                             <p style={{ backgroundColor: "red" }}>{name}</p>
  //                           </div>
  //                           // )}
  //                           // </Draggable>
  //                         );
  //                       })}
  //                     </div>
  //                   )}
  //                 </Draggable>
  //               );
  //             })}
  //             {provided.placeholder}
  //           </div>
  //         )}
  //       </Droppable>
  //     </DragDropContext>
  //   </TestWrapper>
  // );
  const onDragEnd = ({ destination, source, draggableId, type }) => {
    console.log("abc", destination);
    if (!destination) return;
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }

    const start = starter.columns[source.droppableId];
    const end = starter.columns[destination.droppableId];

    if (type === "column") {
      console.log(destination, source, draggableId);
      const newOrder = [...starter.columnOrder];
      newOrder.splice(source.index, 1);
      newOrder.splice(destination.index, 0, draggableId);

      setStarter({
        ...starter,
        columnOrder: newOrder,
      });
      return;
    }

    if (start === end) {
      const column = starter.columns[source.droppableId];
      const taskIds = [...column.taskIds];
      taskIds.splice(source.index, 1);
      taskIds.splice(destination.index, 0, draggableId);
      const newColumn = {
        ...column,
        taskIds,
      };
      setStarter({
        ...starter,
        columns: {
          ...starter.columns,
          [column.id]: newColumn,
        },
      });
      return;
    }

    const startTaskIds = [...start.taskIds];
    const endTaskIds = [...end.taskIds];

    startTaskIds.splice(source.index, 1);
    endTaskIds.splice(destination.index, 0, draggableId);

    const newStartColumn = {
      ...start,
      taskIds: startTaskIds,
    };
    const endTaskColumn = {
      ...end,
      taskIds: endTaskIds,
    };

    setStarter({
      ...starter,
      columns: {
        ...starter.columns,
        [start.id]: newStartColumn,
        [end.id]: endTaskColumn,
      },
    });
    console.log("new starter", starter);

    console.log(destination, source, draggableId);
  };
  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <Droppable droppableId="all-column" type="column" direction="horizontal">
        {(provided, snapshot) => (
          <div
            isDraggingOver={snapshot.isDraggingOver}
            {...provided.droppableProps}
            ref={provided.innerRef}
            style={{ display: "flex" }}
          >
            {starter.columnOrder.map((columnId, index) => {
              const column = starter.columns[columnId];
              const tasks = column.taskIds.map(
                (taskId) => starter.tasks[taskId]
              );

              return (
                <Column
                  index={index}
                  key={column.id}
                  column={column}
                  tasks={tasks}
                />
              );
            })}
            {provided.placeholder}
          </div>
        )}
      </Droppable>
    </DragDropContext>
  );
};

export default Test;
